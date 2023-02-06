package com.minersdream.world.structure;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.BuiltinStructureSets;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.PostPlacementProcessor;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import org.apache.logging.log4j.Level;
import org.slf4j.Logger;

import java.util.Optional;

public class PlaceOreNodes extends StructureFeature<JigsawConfiguration> {

    public static final Logger LOGGER = LogUtils.getLogger();

    public PlaceOreNodes() {
        // Cria o layout das peças da estrutura e entrega para o jogo
        super(JigsawConfiguration.CODEC, PlaceOreNodes::createPiecesGenerator, PostPlacementProcessor.NONE);
    }

    /**
     *        : AVISO!!! NÃO ESQUEÇA ESSE MÉTODO!!!! :
     * Se você não substituir o método step, sua estrutura IRÁ travar o bioma enquanto está sendo analisado!
     *
     * Etapa de geração para quando gerar a estrutura. existem 10 estágios que você pode escolher!
     * Este estágio de estrutura de superfície coloca a estrutura antes que plantas e minérios sejam gerados.
     */
    @Override
    public GenerationStep.Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    /*
     * É aqui que verificações extras podem ser feitas para determinar se a estrutura pode aparecer aqui.
     * Isso só precisa ser substituído se você estiver adicionando condições de spawn adicionais.
     *
     * Curiosidade, se você definir a separação/espaçamento da estrutura como 0/1, poderá usar
     * isFeatureChunk para retornar true somente se determinadas coordenadas de chunk forem passadas
     * que permite gerar estruturas apenas em determinadas coordenadas do mundo.
     *
     * Basicamente, este método é usado para determinar se o terreno está em uma altura adequada,
     * se certas outras estruturas estiverem muito próximas ou não, ou alguma outra condição restritiva.
     *
     * Por exemplo, Pillager Outposts adicionou uma verificação para certificar-se de que não pode aparecer dentro de 10 blocos de uma vila.
     * (Bedrock Edition parece não ter o mesmo cheque)
     *
     * Se você estiver fazendo estruturas do Nether, provavelmente desejará gerar sua estrutura no topo das bordas.
     * A melhor maneira de fazer isso é usar getBaseColumn para pegar uma coluna de blocos na posição x/z da estrutura.
     * Em seguida, percorra-o e procure por terra com ar acima dele e defina o valor Y de blockpos para ele.
     * Certifique-se de definir o booleano final em JigsawPlacement.addPieces como false para
     * que a estrutura é gerada no valor y do blockpos em vez de colocar a estrutura no telhado de Bedrock!
     *
     * Além disso, pelo amor de Deus, não faça verificação de dimensão aqui.
     * Se você fizer isso e a dimensão de outro mod estiver tentando gerar sua estrutura,
     * o comando de localização fará o minecraft travar para sempre e quebrar o jogo.
     * Use as tags de bioma para onde gerar a estrutura e os usuários podem datapack
     * para gerar em biomas específicos que não estão na dimensão que eles não gostam, se desejarem.
     */
    private static boolean isFeatureChunk(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        // Pega a posição do pedaço em que estamos
        ChunkPos chunkpos = context.chunkPos();

        // Verifica para garantir que nossa estrutura não apareça dentro de 10 pedaços de um Monumento do Oceano
        // para demonstrar como esse método é bom para verificar condições extras para desova
        return !context.chunkGenerator().hasFeatureChunkInRange(BuiltinStructureSets.OCEAN_MONUMENTS, context.seed(), chunkpos.x, chunkpos.z, 10);
    }

    public static Optional<PieceGenerator<JigsawConfiguration>> createPiecesGenerator(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {

        // Verifique se a vaga é válida para nossa estrutura. Este é apenas um outro método de limpeza.
        // Retornar um opcional vazio diz ao jogo para pular este ponto, pois não irá gerar a estrutura.
        if (!PlaceOreNodes.isFeatureChunk(context)) {
            return Optional.empty();
        }

        // Transforma as coordenadas do bloco em coordenadas reais que podemos usar. (Obtém o centro desse pedaço)
        BlockPos blockpos = context.chunkPos().getMiddleBlockPosition(0);

        // Encontre o valor Y superior do terreno e, em seguida, desloque nossa estrutura para 60 blocos acima disso.
        // WORLD_SURFACE_WG irá parar na água superior para que não coloquemos acidentalmente nossa estrutura no oceano se for um oceano super profundo.
        int topLandY = context.chunkGenerator().getFirstFreeHeight(blockpos.getX(), blockpos.getZ(), Heightmap.Types.WORLD_SURFACE_WG, context.heightAccessor());
        blockpos = blockpos.above(topLandY -9);

        Optional<PieceGenerator<JigsawConfiguration>> structurePiecesGenerator =
                JigsawPlacement.addPieces(
                        context, // Usado para JigsawPlacement para obter todos os comportamentos adequados.
                        PoolElementStructurePiece::new, // Necessário para criar uma lista de peças do quebra-cabeça ao fazer o layout da estrutura.
                        blockpos, // Posição da estrutura. O valor Y será ignorado se o último parâmetro for definido como verdadeiro.
                        false, // Ajustes especiais de limites para aldeias. É difícil de explicar. Mantenha isso falso e faça com que suas peças não se cruzem parcialmente.
                        // Sem interseção ou totalmente contido fará com que as peças dos filhos apareçam bem. É mais fácil assim.
                        false // Coloque no mapa de altura (Overworld). Defina como false para que a estrutura seja colocada no valor Y passado no blockpos.
                        // Definitivamente, mantenha isso falso ao colocar estruturas no nether, caso contrário, a colocação do mapa de altura colocará a estrutura no telhado de Bedrock.
                );

        /*
         * Nota, você está sempre livre para fazer sua própria classe JigsawPlacement e implementação de como a estrutura
         * deve gerar. É complicado, mas extremamente poderoso se você estiver fazendo algo que o sistema de quebra-cabeças do vanilla não pode fazer.
         * Como, por exemplo, forçar 3 peças a sempre aparecerem todas as vezes, limitar a frequência com que uma peça aparece ou remover a limitação de interseção de peças.
         *
         * Um exemplo de um JigsawPlacement.addPieces personalizado em ação pode ser encontrado aqui (aviso, ele está usando mapeamentos Mojmap):
         * https://github.com/TelepathicGrunt/RepurposedStructures/blob/1.18.2/src/main/java/com/telepathicgrunt/repurposedstructures/world/structures/pieces/PieceLimitedJigsawManager.java
         */

        if(structurePiecesGenerator.isPresent()) {
            // Eu uso para depurar e descobrir rapidamente se a estrutura está gerando ou não e onde está.
            // Isso está retornando as coordenadas da peça inicial do centro.
            LOGGER.info("[MD]: um nodo de recurso foi gerado em {}", blockpos, Level.DEBUG);
        }


        // Retorna o gerador de peças que agora está configurado para que o jogo o execute quando precisar criar o layout das peças da estrutura.
        return structurePiecesGenerator;
    }
}